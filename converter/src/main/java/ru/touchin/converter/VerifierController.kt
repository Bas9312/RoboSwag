package ru.touchin.converter

import ru.touchin.converter.commands.Command
import ru.touchin.converter.verifiers.Verifier
import ru.touchin.converter.wrap.InputConvertable
import java.math.BigDecimal

class VerifierController(
        private val inputConvertable: InputConvertable,
        private val verifiers: List<Verifier<BigDecimal>>,
        private val isCorrectionMode: Boolean
) {

    fun verifyInput(): Boolean = verifyAll(inputConvertable.input.getText().toString())

    fun verifyAll(inputString: String): Boolean {
        return verifiers.all { verifier ->
            if (inputString.isBlank() == true) {
                true
            } else {
                val result = verifier.verify(inputString)
                if (isCorrectionMode == true) execute(result)

                result is Command.Success
            }
        }
    }

    fun execute(command: Command<BigDecimal>) {
        when (command) {
            is Command.Fallback -> {
                with(inputConvertable) {
                    setNumber(storedValue, addSuffix = false)
                }
            }
            is Command.Set -> {
                inputConvertable.setNumber(command.data, addSuffix = false)
            }
            is Command.Remove -> {
                with(inputConvertable) {
                    val changedString = format(storedValue).dropLast(command.data.toInt())
                    inputConvertable.setNumber(format(changedString), placeCursorToTheEnd = true, addSuffix = false)
                }
            }
            is Command.Success -> { // do nothing
            }
            is Command.Error -> {
                throw IllegalStateException("Can't resolve error")
            }
        }
    }

}
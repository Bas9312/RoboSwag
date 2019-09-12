package ru.touchin.edittextutils.verifier

import ru.touchin.edittextutils.commands.Command
import ru.touchin.edittextutils.verifier.verifiers.Verifier
import ru.touchin.edittextutils.converter.wrap.InputConvertible
import java.math.BigDecimal

class VerifierController(
        private val inputConvertible: InputConvertible,
        private val verifiers: List<Verifier<BigDecimal>>,
        private val isCorrectionMode: Boolean = false
) {

    fun isInputValid(): Boolean = verifyAll(inputConvertible.input.getText().toString())

    fun verifyAll(inputString: String, isCorrectionPossible: Boolean = false): Boolean {
        return verifiers.all { verifier ->
            if (inputString.isBlank()) {
                true
            } else {
                val result = verifier.verify(inputString)
                if (isCorrectionMode || isCorrectionPossible) execute(result)

                result is Command.Success
            }
        }
    }

    fun execute(command: Command<BigDecimal>) {
        when (command) {
            is Command.Fallback -> {
                with(inputConvertible) {
                    setNumber(storedValue)
                }
            }
            is Command.Set -> {
                inputConvertible.setNumber(command.data)
            }
            is Command.Remove -> {
                with(inputConvertible) {
                    val changedString = format(storedValue).dropLast(command.data.toInt())
                    inputConvertible.setNumber(format(changedString), placeCursorToTheEnd = true)
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

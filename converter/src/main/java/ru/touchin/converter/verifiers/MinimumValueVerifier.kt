package ru.touchin.converter.verifiers

import ru.touchin.converter.commands.Command
import java.math.BigDecimal

class MinimumValueVerifier(val maxValue: BigDecimal) : Verifier<BigDecimal> {

    override fun verify(text: String): Command<BigDecimal> = if (text.toBigDecimal() >= maxValue) {
        Command.Success()
    } else {
        Command.Fallback()
    }

}
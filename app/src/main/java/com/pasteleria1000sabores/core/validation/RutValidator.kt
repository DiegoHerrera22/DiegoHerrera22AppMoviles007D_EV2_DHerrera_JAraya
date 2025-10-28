package com.pasteleria1000sabores.core.validation

/**
 * Utility object for validating Chilean RUT numbers.  A RUT consists of a
 * numeric part followed by a verification digit (which may be 'K').  This
 * function implements the standard modulus 11 algorithm to verify that the
 * check digit matches the numeric part.  It ignores dots and dashes and
 * accepts both uppercase and lowercase 'k'.
 */
object RutValidator {
    fun isValidRut(rut: String): Boolean {
        // Remove dots and hyphens.
        val cleaned = rut.replace(".", "").replace("-", "").uppercase()
        if (cleaned.length < 2) return false
        val body = cleaned.dropLast(1)
        val dv = cleaned.last()
        if (!body.all { it.isDigit() }) return false
        val digits = body.map { it.toString().toInt() }
        var multiplier = 2
        var sum = 0
        for (digit in digits.reversed()) {
            sum += digit * multiplier
            multiplier = if (multiplier < 7) multiplier + 1 else 2
        }
        val remainder = 11 - (sum % 11)
        val expectedDv = when (remainder) {
            11 -> '0'
            10 -> 'K'
            else -> remainder.toString()[0]
        }
        return expectedDv == dv
    }
}
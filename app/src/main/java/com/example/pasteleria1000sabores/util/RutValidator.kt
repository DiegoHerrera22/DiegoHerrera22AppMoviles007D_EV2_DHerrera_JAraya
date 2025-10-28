package com.example.pasteleria1000sabores.util

/**
 * Validador de RUT chileno. Se encarga de verificar si un RUT
 * proporcionado es válido según el algoritmo del dígito verificador.
 */
object RutValidator {
    /**
     * Valida un RUT chileno. El RUT puede contener puntos y guión, los cuales
     * serán eliminados para el cálculo. El dígito verificador puede ser un
     * número entre 0 y 9 o la letra K.
     *
     * @param rut RUT en formato libre (con o sin puntos/guión)
     * @return true si el RUT es válido, false en caso contrario
     */
    fun isValid(rut: String): Boolean {
        val clean = rut.replace(".", "").replace("-", "").uppercase()
        // Debe tener al menos 2 caracteres: cuerpo y dígito verificador
        if (clean.length < 2) return false
        // El dígito verificador es el último carácter
        val dvExpected = clean.last()
        val body = clean.dropLast(1)
        // El cuerpo debe ser numérico
        if (!body.all { it.isDigit() }) return false
        // Algoritmo de cálculo del dígito
        var sum = 0
        var multiplier = 2
        for (digitChar in body.reversed()) {
            sum += (digitChar - '0') * multiplier
            multiplier = if (multiplier < 7) multiplier + 1 else 2
        }
        val dvCalc = 11 - (sum % 11)
        val dvChar = when (dvCalc) {
            11 -> '0'
            10 -> 'K'
            else -> '0' + dvCalc
        }
        return dvChar == dvExpected
    }
}
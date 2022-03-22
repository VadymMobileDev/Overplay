package com.example.overplaytest.util

class Quaternion : Vector() {
    private val tmpVector = Vector()
    fun set(quat: Quaternion?) {
        copyVec4(quat!!)
    }

    fun multiplyByQuat(input: Quaternion, output: Quaternion) {
        if (input !== output) {
            output.points[3] =
                points[3] * input.points[3] - points[0] * input.points[0] - points[1] * input.points[1] - (points[2]
                        * input.points[2])
            output.points[0] =
                (points[3] * input.points[0] + points[0] * input.points[3] + points[1] * input.points[2] - points[2]
                        * input.points[1])
            output.points[1] =
                (points[3] * input.points[1] + points[1] * input.points[3] + points[2] * input.points[0] - points[0]
                        * input.points[2])
            output.points[2] =
                (points[3] * input.points[2] + points[2] * input.points[3] + points[0] * input.points[1] - points[1]
                        * input.points[0])
        } else {
            tmpVector.points[0] = input.points[0]
            tmpVector.points[1] = input.points[1]
            tmpVector.points[2] = input.points[2]
            tmpVector.points[3] = input.points[3]
            output.points[3] =
                points[3] * tmpVector.points[3] - points[0] * tmpVector.points[0] - (points[1]
                        * tmpVector.points[1]) - points[2] * tmpVector.points[2]
            output.points[0] =
                points[3] * tmpVector.points[0] + points[0] * tmpVector.points[3] + (points[1]
                        * tmpVector.points[2]) - points[2] * tmpVector.points[1]
            output.points[1] =
                points[3] * tmpVector.points[1] + points[1] * tmpVector.points[3] + (points[2]
                        * tmpVector.points[0]) - points[0] * tmpVector.points[2]
            output.points[2] =
                points[3] * tmpVector.points[2] + points[2] * tmpVector.points[3] + (points[0]
                        * tmpVector.points[1]) - points[1] * tmpVector.points[0]
        }
    }

    override fun multiplyByScalar(scalar: Float) {
        multiplyByScalar(scalar)
    }

    private fun loadIdentityQuat() {
        x = 0f
        y = 0f
        z = 0f
        w = 1f
    }

    init {
        loadIdentityQuat()
    }
}
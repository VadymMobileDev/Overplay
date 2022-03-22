package com.example.overplaytest.util

open class Vector {
    var points = floatArrayOf(0f, 0f, 0f, 0f)
    fun array(): FloatArray {
        return points
    }

    fun copyVec4(vec: Vector) {
        points[0] = vec.points[0]
        points[1] = vec.points[1]
        points[2] = vec.points[2]
        points[3] = vec.points[3]
    }

    open fun multiplyByScalar(scalar: Float) {
        points[0] *= scalar
        points[1] *= scalar
        points[2] *= scalar
        points[3] *= scalar
    }

    var x: Float
        get() = points[0]
        set(x) {
            points[0] = x
        }

    var y: Float
        get() = points[1]
        set(y) {
            points[1] = y
        }

    var z: Float
        get() = points[2]
        set(z) {
            points[2] = z
        }

    var w: Float
        get() = points[3]
        set(w) {
            points[3] = w
        }

    fun w(): Float {
        return points[3]
    }

    fun w(w: Float) {
        points[3] = w
    }

    init {
        points[0] = 0F
        points[1] = 0F
        points[2] = 0F
        points[3] = 0F
    }
}
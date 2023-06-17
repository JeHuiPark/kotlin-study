package com.example.testFactory

class SwitchImpl : Switch {

    private var _isOn = false
    private var _controlCount = 0
    override fun turnOn() {
        _isOn = true
        _controlCount++
    }

    override fun turnOff() {
        _isOn = false
        _controlCount++
    }

    override val isOn: Boolean
        get() = _isOn

    val controlCount: Int
        get() = _controlCount
}

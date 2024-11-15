package com.example.ejercicio_telefonos.telephone

class Telephone(
    private val telephoneNumber: Int,
)
{
    private var actions: TelephoneActions = object : TelephoneActions
    {
        override fun onReceivingCall(callingFromNumber: Int) {}
        override fun onOtherHangUp() {}
    };
    private var otherInTheCall: Int = -1;

    fun getTelephoneNumber(): Int
    {
        return telephoneNumber;
    }

    fun hangUp()
    {
        otherInTheCall = -1;
        Operadora.endCall(telephoneNumber);
    }

    fun call(telephoneNumber: Int): Boolean
    {
        if (!Operadora.sendCall(this.telephoneNumber, telephoneNumber)) return false;

        otherInTheCall = telephoneNumber;
        return true;
    }

    fun onReceiveCall(callingFrom: Int)
    {
        otherInTheCall = callingFrom;
        actions.onReceivingCall(callingFrom);
    }

    fun isInACall(): Boolean
    {
        return otherInTheCall != -1;
    }

    fun setTelephoneActions(actions: TelephoneActions)
    {
        this.actions = actions;
    }

    fun onOtherHangUp()
    {
        otherInTheCall = -1;
        actions.onOtherHangUp();
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Telephone

        return telephoneNumber == other.telephoneNumber
    }

    override fun hashCode(): Int
    {
        return telephoneNumber
    }

    interface TelephoneActions
    {
        fun onReceivingCall(callingFromNumber: Int);
        fun onOtherHangUp();
    }
}
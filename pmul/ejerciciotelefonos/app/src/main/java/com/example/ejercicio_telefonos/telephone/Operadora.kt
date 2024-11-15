package com.example.ejercicio_telefonos.telephone

object Operadora
{
    private var telephones: HashMap<Int, Telephone> = HashMap();
    private val calls: HashMap<Int, Int> = HashMap();

    fun endCall(endingCallFrom: Int)
    {
        val otherInCall = getOtherInCallWith(endingCallFrom);

        if (calls.containsKey(endingCallFrom)) endCallByCallerNumber(endingCallFrom);
        else if (calls.containsKey(otherInCall)) endCallByCallerNumber(otherInCall);
        else throw IllegalStateException("No call found");

        telephones[otherInCall]!!.onOtherHangUp();
    }

    fun endCallByCallerNumber(callerNumber: Int)
    {
        if (!calls.containsKey(callerNumber)) throw IllegalStateException("No call found");

        calls.remove(callerNumber);
    }

    fun sendCall(callingFrom: Int, callingTo: Int): Boolean
    {
        if (!telephones.containsKey(callingTo)) return false;
        if (isInACall(callingTo)) return false;

        calls[callingFrom] = callingTo;
        telephones[callingTo]!!.onReceiveCall(callingFrom);
        return true;
    }

    private fun isReceivingCall(telephoneId: Int): Boolean
    {
        return calls.containsValue(telephoneId);
    }

    private fun isCallingSomeone(telephoneId: Int): Boolean
    {
        return calls.containsKey(telephoneId);
    }

    private fun isInACall(telephoneId: Int): Boolean
    {
        return isReceivingCall(telephoneId) || isCallingSomeone(telephoneId);
    }

    private fun getOtherInCallWith(telephoneId: Int): Int
    {
        if (isCallingSomeone(telephoneId)) return calls[telephoneId]!!;
        if (isReceivingCall(telephoneId)) return calls.filter { it.value == telephoneId }.keys.first();
        throw IllegalStateException("No call found");
    }

    private fun getCaller(telephoneId: Int): Int
    {
        if (isCallingSomeone(telephoneId)) return telephoneId;
        if (isReceivingCall(telephoneId)) return calls.filter { it.value == telephoneId }.keys.first();
        throw IllegalStateException("No call found");
    }

    fun addTelephone(telephone: Telephone)
    {
        telephones[telephone.getTelephoneNumber()] = telephone;
    }
}
package com.example.ejercicio_telefonos.telephone

import com.example.ejercicio_telefonos.telephone.TelephoneFragment.TelephoneFragmentActions

object Operadora
{
    private var telephones: ArrayList<TelephoneFragment> = ArrayList();
    private val calls: HashMap<Int, Int> = HashMap();

    private fun endCall(callerId: Int)
    {
        if (!calls.containsKey(callerId)) throw IllegalArgumentException("No such caller found");

        calls.remove(callerId);
    }

    private fun isReceivingCall(telephoneId: Int): Boolean
    {
        return calls.containsValue(telephoneId);
    }

    private fun isCallingSomeone(telephoneId: Int): Boolean
    {
        return calls.containsKey(telephoneId);
    }

    fun addTelephone(telephoneFragment: TelephoneFragment)
    {
        telephones.add(telephoneFragment);

        val telephoneId = telephones.size;

        telephoneFragment.setActions(object : TelephoneFragmentActions
        {
            override fun onReceiveCall(receivingFrom: Int) {}

            override fun onSendCall(callingTo: Int): Boolean
            {
                if (telephones[callingTo - 1].isInACall()) return false;

                calls[getTelephoneNumber()] = callingTo;
                telephones[callingTo - 1].onReceiveCall(getTelephoneNumber());
                return true;
            }

            override fun onOtherHangUp(hangUpFrom: Int) {}

            override fun onHangUp(hangUpTo: Int)
            {
                if (isCallingSomeone(getTelephoneNumber()))
                {
                    endCall(getTelephoneNumber());
                } else if (isReceivingCall(getTelephoneNumber()))
                {
                    endCall(hangUpTo);
                } else
                {
                    throw IllegalStateException("No call to hang up");
                }

                telephones[hangUpTo - 1].onOtherHangUp(getTelephoneNumber());
            }

            override fun getTelephoneNumber(): Int
            {
                return telephoneId;
            }
        });
    }


}
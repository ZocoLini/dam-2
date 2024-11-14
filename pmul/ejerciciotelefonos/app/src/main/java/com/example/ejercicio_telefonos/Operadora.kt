package com.example.ejercicio_telefonos

import com.example.ejercicio_telefonos.TelefonoFragment.TelefonoFragmentActions

object Operadora
{
    private var telefonos: ArrayList<TelefonoFragment> = ArrayList();

    fun addTelefono(telefono: TelefonoFragment)
    {
        telefonos.add(telefono);

        val telefonoId = telefonos.size - 1;

        telefono.setActions(object : TelefonoFragmentActions
        {
            override fun onTelefonoReceiveCall(telefonoId: Int, telefonoFragment: TelefonoFragment)
            {

            }

            override fun onTelefonoSendCall(telefonoId: Int, telefonoFragment: TelefonoFragment): Boolean
            {
                if (!telefonos[telefonoId].isAvailable()) return false;

                telefonos[telefonoId].onReceiveCall(telefonos.indexOf(telefonoFragment));
                return true;
            }

            override fun onTelefonoContestar(telefonoId: Int, telefonoFragment: TelefonoFragment)
            {

            }

            override fun onTelefonoColgar(telefonoId: Int, telefonoFragment: TelefonoFragment)
            {
            }

            override fun getTelefonoId(): Int
            {
                return telefonoId;
            }
        });
    }


}
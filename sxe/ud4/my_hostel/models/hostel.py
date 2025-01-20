from odoo import models, fields

class Hostel(models.Model):
    _name="myhostel.hostel"
    _description="Tabla para la aplicacion"

    nombre = fields.Char(string = "Nombre", required = True)
    codigo = fields.Char(string = "Codigo", required = True)
    direccion1 = fields.Char("Direccion1")
    direccion2 = fields.Char("Direccion2")
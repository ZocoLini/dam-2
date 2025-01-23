from odoo import fields, models


class Hostel(models.Model):
    _name = "hostel.hostel"
    _description = "Information about hostel"

    Nombre = fields.Char(string="Nombre", required=True)
    Codigo = fields.Char(string="Codigo", required=True)
    Direccion1 = fields.Char("Direccion1")
    Direccion2 = fields.Char("Direccion2")

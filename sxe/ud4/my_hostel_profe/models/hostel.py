from odoo import fields, models


class Hostel(models.Model):
    _name = "hostel.hostel"
    _description = "Information about hostel"

    Nombre = fields.Char(string="Nombre", required=True)
    Codigo = fields.Char(string="Codigo", required=True)
    Direccion1 = fields.Char("Direccion1")
    Direccion2 = fields.Char("Direccion2")

    dias = fields.Integer(string="Dias", defaault=0)
    abierto = fields.Boolean(string="Abierto", readonly=True)
    estado = fields.Selection([{'0', 'Bueno'}, {'1', 'Regular'}, {'2', 'Malo'}], string="Estado", default='0')
    propietario = fields.Many2one('hr.employee', string="Propietario")

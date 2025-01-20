from odoo import models, fields

class Lunes(models.Model):
    _name="lunes.lunes"
    _description="Tabla para la aplicacion"

    nombre = fields.Char()
    telefono = fields.Char()
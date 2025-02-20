from odoo import fields, models, api


class Prueba(models.Model):
    _name = 'viajes.prueba'
    _description = 'Modelo de prueba para el examen'

    name = fields.Char(string="Nombre", required=True)

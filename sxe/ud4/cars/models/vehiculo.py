from odoo import fields, models


class Vehiculo(models.Model):
    _name = "cars.vehiculo"
    _description = "Vehiculo del Modulo Cars"

    modelo = fields.Char(string='Modelo', required=True)
    combustible = fields.Selection([
        ('diesel', 'Diesel'),
        ('gasolina', 'Gasolina'),
        ('hibrido', 'Hibrido'),
        ('electrico', 'Electrico'),
    ], string='Combustible', required=True)
    fechaCompra = fields.Date(string='Fecha de Compra')
    matricula = fields.Char(string='Matricula', required=True)

from odoo import fields, models, api
from odoo.exceptions import ValidationError


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
    matricula = fields.Char(string='Matricula',  required=True)
    trabajador = fields.Many2many('res.partner', string='Trabajador')

    @api.constrains('matricula')
    def _check_matricula_length(self):
        for record in self:
            if record.matricula and len(record.matricula) != 7:
                raise ValidationError("La matrícula debe tener exactamente 7 caracteres.")
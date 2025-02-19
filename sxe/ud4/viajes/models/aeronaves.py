from odoo import fields, models, api
from odoo.exceptions import ValidationError


class Aeronaves(models.Model):
    _name = 'viajes.aeronaves'
    _description = 'Aviones para volar'

    name = fields.Char(string="Nombre", required=True)
    matricula = fields.Char(string="Matricula", required=True)
    fecha_compra = fields.Datetime(string="Fecha de Compra", required=True)
    km_actuales = fields.Integer(string="Numero de km actuales", required=True)
    hangar = fields.Selection([{'0', 'Londres'}, {'1', 'Bruselas'}, {'2', 'Luton'}], string="Hangar del avion",
                              required=True)

    destinos = fields.Many2one('viajes.destinos', string="Destinos", required=True, ondelete='cascade')

    @api.constrains('matricula')
    def _check_matriula_len(self):
        for record in self:
            if len(record.matricula) > 7:
                raise ValidationError("La longitud de la matricula no debe ser mayor que 7")

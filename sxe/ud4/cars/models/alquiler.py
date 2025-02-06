from odoo import fields, models, api


class Alquiler(models.Model):
    _name = 'cars.alquiler'
    _description = 'Alquileres de Vehiculos'

    trabajador = fields.Many2one('res.partner', string='Trabajador', required=True)
    tel_movil = fields.Char(string='Telefono Movil', readonly=True)
    ciudad = fields.Char(string='Ciudad', required=True)
    codigo_postal = fields.Char(string='Codigo', required=True)

    num_dias_alquilado = fields.Integer(string='Numero de dias alquilado', required=True)
    estado_actual = fields.Selection([
        ('pendiente', 'Pendiente'),
        ('alquilado', 'Alquilado'),
        ('finalizado', 'Finalizado'),
    ], string='Estado', required=True)
    devuelto = fields.Boolean(string='Devuelto(no/si)', default=False)
    coche = fields.Many2one('cars.vehiculo', string='Coche', required=True)

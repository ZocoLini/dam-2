from odoo import fields, models, api


class Hostel(models.Model):
    _name = "hostel.hostel"
    _description = "Information about hostel"

    Nombre = fields.Char(string="Nombre", required=True)
    Codigo = fields.Char(string="Codigo", required=True)
    Direccion1 = fields.Char("Direccion1")
    Direccion2 = fields.Char("Direccion2")

    dias = fields.Integer(string="Dias", defaault=0)
    abierto = fields.Boolean(string="Abierto", compute="_compute_urgente", readonly=True)
    estado = fields.Selection([{'0', 'Bueno'}, {'1', 'Regular'}, {'2', 'Malo'}], string="Estado", default='0')
    propietario = fields.Many2one('hr.employee', string="Propietario")

    @api.depends('dias')
    def _compute_urgente(self):
        for record in self:
            if record.dias > 7:
                record.abierto = True
            else:
                record.abierto = False

    @api.constraints('dias')
    def _check_dias(self):
        for record in self:
            if record.alias > 7:
                raise ValidationError("Validacion para el campo")

from odoo import fields, models, api


class License(models.Model):
    _name = "trt.user"
    _description = "The Round Table user"

    name = fields.Char(string="Email", required=True)
    password = fields.Char(string="Password", required=True)

    licenses = fields.One2many('trt.license', 'user', string="Licenses")

    # @api.depends('dias')
    # def _compute_urgente(self):
    #     for record in self:
    #         if record.dias > 7:
    #             record.abierto = True
    #         else:
    #             record.abierto = False

    # @api.constraints('dias')
    # def _check_dias(self):
    #     for record in self:
    #         if record.alias > 7:
    #             raise ValidationError("Validacion para el campo")
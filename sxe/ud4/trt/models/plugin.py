from odoo import fields, models, api
from odoo.exceptions import ValidationError


class Plugin(models.Model):
    _name = "trt.plugin"
    _description = "Plugins available to download for the software users"

    name = fields.Char(string="Name", required=True)
    description = fields.Text(string="Description")
    is_monthly = fields.Boolean(string="Monthly", readonly=True, compute='_compute_is_monthly')
    price = fields.Float(string="Price", required=True)
    repository = fields.Char(string="Repository")

    licenses = fields.Many2many('trt.license', string="Licenses")

    @api.depends('price')
    def _compute_is_monthly(self):
        for record in self:
            record.is_monthly = record.price > 0

    @api.constrains('price')
    def _check_min_price(self):
        for record in self:
            if record.price < 0:
                raise ValidationError("The price must be equal or greater than 0")
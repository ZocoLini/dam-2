{
    "name": "intituto APLICACION",  # Module title
    "summary": "Manage Hostel easily",  # Module subtitle phrase
    "description": "Efficiently manage the entire residential facility in the school.",  # Supports reStructuredText(RST) format (description is Deprecated)
    "version": "17.0.1.0.0",
    "author": "Borja Castellano Extremaoduro",
    "category": "Tools",
    "website": "https://www.lebastudios.org",
    "license": "AGPL-3",
    "depends": ["base"],
    "data": [
    	"security/instituto_security.xml",
        "security/ir.model.access.csv",
        "views/intituto.xml",
	],
    "installable": True,
}
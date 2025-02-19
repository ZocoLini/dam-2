{
    "name": "The Round Table Software Managment",
    "summary": "Manage users, plugins and licenses related to TRT",
    "description": "Module to manage users, plugins and licenses related to The Round Table software",
    "version": "17.0.1.0.8",
    "author": "LebaStudios Software Solutions",
    "category": "Tools",
    "website": "https://www.lebastudios.org",
    "license": "AGPL-3",
    "depends": ["base"],
    "data": [
    	"security/trt_security.xml",
        "security/ir.model.access.csv",
        "views/trt.xml",
        "views/license.xml",
        "views/plugin.xml",
        "views/user.xml"
	],
    "installable": True,
}

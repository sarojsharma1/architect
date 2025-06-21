from RPA.Archive import Archive
from RPA.Database import Database
from RPA.Desktop import Desktop
from RPA.Crypto import Crypto

desktop = Desktop()
lib = Database()
libo = Archive()
desktop.open_application("erp_client.exe")
cr = Crypto()
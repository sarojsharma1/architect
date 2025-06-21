import subprocess
import os
import sys

venv_dir = "env"

subprocess.run([sys.executable, "-m", "venv", venv_dir], check=True)
pip_path = os.path.join(venv_dir, "Scripts", "pip.exe") if os.name == "nt" else os.path.join(venv_dir, "bin", "pip")
python_path = os.path.join(venv_dir, "Scripts", "python.exe") if os.name == "nt" else os.path.join(venv_dir, "bin", "python")
subprocess.run([pip_path, "install", "-r", "requirements.txt"], check=True)
subprocess.run([python_path, "-m", "uvicorn", "main:app", "--reload"], check=True)
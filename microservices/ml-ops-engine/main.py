from fastapi import FastAPI
import py_eureka_client.eureka_client as eureka_client
from contextlib import asynccontextmanager

@asynccontextmanager
async def lifespan(app: FastAPI):
    await eureka_client.init_async(
        eureka_server="http://localhost:8761/eureka",
        app_name="ml-ops-engine",
        instance_port=9090,
    )
    yield
    await eureka_client.stop_async()

app = FastAPI(lifespan=lifespan)
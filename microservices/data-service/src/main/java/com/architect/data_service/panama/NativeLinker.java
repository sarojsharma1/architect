package com.architect.data_service.panama;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class NativeLinker {
    public static void main(String[] args) {
        Linker linker = Linker.nativeLinker();
        SymbolLookup symbolLookup = SymbolLookup.loaderLookup();
        MemorySegment memorySegment = symbolLookup.findOrThrow("test");
        MethodHandle methodHandle = linker.downcallHandle(
                memorySegment,
                FunctionDescriptor.of(
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT
                )
        );
        try {
            methodHandle.invokeExact(5, 7);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

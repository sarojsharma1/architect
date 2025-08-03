package com.architect.data_service.file_util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    public List<FileRecordInfo> getFileDetails(FileMetaDataDto fileMetaInfo) {
        Map<String, List<String>> record = new LinkedHashMap<>();
        String connectorName = fileMetaInfo.getConnectorName();
        switch (connectorName) {
            case "ASCII (Delimited)" -> {
                record = transformRecord(delimitedFileParser(fileMetaInfo, 100, "fileMetaData"));
            }
            case "Unicode (Delimited)" -> {
                List<List<String>> records = delimitedFileParser(fileMetaInfo, 100, "fileMetaData");
                List<String> cleanHeader = records.getFirst().stream().map(FileRecordAnalyzer::replaceNonPrintableUnicodeCharacter).toList();
                records.removeFirst();
                records.addFirst(cleanHeader);
                record = transformRecord(records);
            }
            case "ASCII (Fixed)" -> {
                record = transformRecord(fixedWidthFileParser(fileMetaInfo, 100, "fileMetaData"));
            }
            case "Excel" -> {
                record = excelFileParser(fileMetaInfo);
            }
        }
        return inferDataType(connectorName, record);
    }

    public List<List<Map<String, String>>> getTabularPreview(FileMetaInfo fileMetaInfo) {
        List<List<Map<String, String>>> table = new ArrayList<>();
        switch (fileMetaInfo.getConnectorName()) {
            case "ASCII (Delimited)", "Unicode (Delimited)" -> {
                table = prepareTabularData(delimitedFileParser(fileMetaInfo, 10, "filePreview"));
            }
            case "ASCII (Fixed)" -> {
                table = prepareTabularData(fixedWidthFileParser(fileMetaInfo, 10, "filePreview"));
            }
        }
        return table;
    }
}

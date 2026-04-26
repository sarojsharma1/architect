function Rename-FilesInFolder(
        [parameter(Mandatory=$true)][string]$Path,
        [parameter(Mandatory=$false)][string]$FilePattern = "",
        [parameter(Mandatory=$true)][int]$Days,
        [parameter(Mandatory=$false)][bool]$Prefix=$true)
{
    [bool]$HasError = $false
    [bool]$TmpError = $false

    $Days = $Days * -1
    if($FilePattern -eq ""){[array]$ArrayInclude = @('*.*')}
    else{$ArrayInclude = $FilePattern.Split(",")}

    Get-ChildItem -Path $Path -Include $ArrayInclude -Recurse | % {
        if(!$_.PSIsContainer -and $_.LastWriteTime -lt (Get-Date).AddDays($Days))
        {
            $OutFileExt = $_.Extension
            $BaseName = $_.Name.Replace($OutFileExt,"")
            $FullFileName = $_.FullName

            if(!$_.Name.StartsWith((Get-Date -Format "yyyyMMdd").Substring(0,4)) -and $BaseName -notmatch "\d{4}\b")
            {
                $FolderLocation = Split-Path $_.FullName
                if($Prefix -eq $true){
                    $OutFileName = (Get-Date -Format "yyyyMMdd") + "_" + $_.Name
                    $OutFileFullPath = "$FolderLocation\$OutFileName"
                }
                else{
                    $OutFileName = $BaseName + (Get-Date -Format "_yyyyMMdd") + $OutFileExt
                    $OutFileFullPath = "$FolderLocation\$OutFileName"
                }

                # Write-Log "Renaming $FullFileName to $OutFileFullPath"  # <-- external logger, commented out
                Write-Host "Renaming $FullFileName to $OutFileFullPath"

                $TmpError = Rename-Files -InFileFullName $_.FullName -OutFileFullName $OutFileFullPath
                if($TmpError){$HasError = $true}
            }
        }
    }
    return $HasError
}


function Rename-Files(
        [parameter(Mandatory=$true)][string]$InFileFullName,
        [parameter(Mandatory=$true)][string]$OutFileFullName)
{
    [bool]$HasError = $false

    $Props = @{
    # LogTable = $LOG_TABLE_FILE  # <-- external variable, commented out
        LogTable     = $null
        Command      = $null
        InFile       = $InFileFullName
        OutFile      = $null
        HasError     = $HasError
        ErrorCode    = $null
        Message      = $null
        ErrorMessage = $null
        EventGUID    = [guid]::NewGuid().ToString()
    }
    $LogInfo = New-Object PSObject -Property $Props

    # Log-Object $LogInfo  # <-- external logger, commented out
    Write-Host "[PRE ] in='$($LogInfo.InFile)' cmd='$($LogInfo.Command)' guid='$($LogInfo.EventGUID)'"

    $OutFileLocation = Split-Path $OutFileFullName
    $OutFileNewName  = Split-Path -Leaf $OutFileFullName

    $LogInfo.Command = "Rename-Item -Path $InFileFullName -NewName $OutFileNewName"

    try{ Rename-Item -Path $InFileFullName -NewName $OutFileNewName -ErrorAction Stop }
    catch{ }
    $Success = $?

    if($Success -ne $true){
        $HasError                = $true
        $LogInfo.OutFile         = $OutFileFullName
        $LogInfo.ErrorCode       = -1
        $LogInfo.Message         = $_.FullyQualifiedErrorId
        $LogInfo.ErrorMessage    = $_.Exception.Message
        $LogInfo.HasError        = $HasError
    }
    else{
        $LogInfo.OutFile         = $OutFileFullName
        $LogInfo.ErrorCode       = 0
        $LogInfo.Message         = "Success"
    }

    # Log-Object $LogInfo  # <-- external logger, commented out
    Write-Host "[POST] in='$($LogInfo.InFile)' out='$($LogInfo.OutFile)' code='$($LogInfo.ErrorCode)' msg='$($LogInfo.Message)' err='$($LogInfo.ErrorMessage)'"

    return $HasError
}

# Only Rename-FilesInFolder is public. Rename-Files is internal.
Export-ModuleMember -Function Rename-FilesInFolder
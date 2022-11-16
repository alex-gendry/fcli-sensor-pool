package com.fortify.cli.tool.fod_uploader.cli.cmd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fortify.cli.common.output.cli.mixin.BasicOutputHelperMixins;
import com.fortify.cli.tool.common.cli.cmd.AbstractToolInstallCommand;
import com.fortify.cli.tool.common.helper.ToolHelper;
import com.fortify.cli.tool.common.helper.ToolInstallDescriptor.ToolVersionInstallDescriptor;
import com.fortify.cli.tool.util.FileUtils;

import picocli.CommandLine.Command;

@Command(name = BasicOutputHelperMixins.Install.CMD_NAME)
public class ToolFoDUploaderInstallCommand extends AbstractToolInstallCommand {
    @Override
    protected String getToolName() {
        return "fod-uploader";
    }
    
    @Override
    protected InstallType getInstallType() {
        return InstallType.COPY;
    }
    
    @Override
    protected void postInstall(ToolVersionInstallDescriptor descriptor, Path installPath, Path binPath) throws IOException {
        Files.createDirectories(binPath);
        FileUtils.copyResourceToDir(ToolHelper.getResourceFile(getToolName(), "extra-files/bin/FoDUpload"), binPath);
        FileUtils.copyResourceToDir(ToolHelper.getResourceFile(getToolName(), "extra-files/bin/FoDUpload.bat"), binPath);
    }
}

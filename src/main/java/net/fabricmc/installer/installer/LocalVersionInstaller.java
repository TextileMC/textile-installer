package net.fabricmc.installer.installer;

import net.fabricmc.installer.util.IInstallerProgress;
import net.fabricmc.installer.util.Translator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Created by modmuss50 on 15/05/2017.
 */
public class LocalVersionInstaller {
	public static void install(File mcDir, IInstallerProgress progress) throws Exception {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(Translator.getString("install.client.selectCustomJar"));
		fc.setFileFilter(new FileNameExtensionFilter(
			"Jar Files", "jar"));
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File inputFile = fc.getSelectedFile();

			JarFile jarFile = new JarFile(inputFile);
			Attributes attributes = jarFile.getManifest().getMainAttributes();
			String mcVersion = attributes.getValue("MinecraftVersion");
			Optional<String> stringOptional = ClientInstaller.isValidInstallLocation(mcDir, mcVersion);
			if (stringOptional.isPresent()) {
				throw new Exception(stringOptional.get());
			}
			ClientInstaller.install(mcDir, mcVersion, progress, inputFile);
		} else {
			throw new Exception("Failed to find jar");
		}

	}

}

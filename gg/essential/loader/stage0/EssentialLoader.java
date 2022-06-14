/*    */ package gg.essential.loader.stage0;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.util.Enumeration;
/*    */ import java.util.jar.Attributes;
/*    */ import java.util.jar.JarInputStream;
/*    */ import java.util.jar.Manifest;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ class EssentialLoader {
/*    */   static final String STAGE1_RESOURCE = "gg/essential/loader/stage0/stage1.jar";
/*    */   static final String STAGE1_PKG = "gg.essential.loader.stage1.";
/* 18 */   static final String STAGE1_PKG_PATH = "gg.essential.loader.stage1.".replace('.', '/');
/* 19 */   static final Logger LOGGER = LogManager.getLogger(EssentialLoader.class);
/*    */   
/*    */   private final String variant;
/*    */   
/*    */   public EssentialLoader(String variant) {
/* 24 */     this.variant = variant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Path loadStage1File(Path gameDir) throws Exception {
/* 32 */     Path dataDir = gameDir.resolve("essential").resolve("loader").resolve("stage0").resolve(this.variant);
/* 33 */     Path stage1UpdateFile = dataDir.resolve("stage1.update.jar");
/* 34 */     Path stage1File = dataDir.resolve("stage1.jar");
/* 35 */     URL stage1Url = stage1File.toUri().toURL();
/*    */     
/* 37 */     if (!Files.exists(dataDir, new java.nio.file.LinkOption[0])) {
/* 38 */       Files.createDirectories(dataDir, (FileAttribute<?>[])new FileAttribute[0]);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 43 */     if (Files.exists(stage1UpdateFile, new java.nio.file.LinkOption[0])) {
/* 44 */       LOGGER.info("Found update for stage1.");
/* 45 */       Files.deleteIfExists(stage1File);
/* 46 */       Files.move(stage1UpdateFile, stage1File, new java.nio.file.CopyOption[0]);
/*    */     } 
/*    */ 
/*    */     
/* 50 */     URL latestUrl = null;
/* 51 */     int latestVersion = -1;
/*    */ 
/*    */     
/* 54 */     if (Files.exists(stage1File, new java.nio.file.LinkOption[0])) {
/* 55 */       latestVersion = getVersion(stage1Url);
/* 56 */       LOGGER.debug("Found stage1 version {}: {}", new Object[] { Integer.valueOf(latestVersion), stage1Url });
/*    */     } 
/*    */     
/* 59 */     Enumeration<URL> resources = EssentialLoader.class.getClassLoader().getResources("gg/essential/loader/stage0/stage1.jar");
/* 60 */     if (!resources.hasMoreElements()) {
/* 61 */       LOGGER.warn("Found no embedded stage1 jar files.");
/*    */     }
/* 63 */     while (resources.hasMoreElements()) {
/* 64 */       URL url = resources.nextElement();
/* 65 */       int version = getVersion(url);
/* 66 */       LOGGER.debug("Found stage1 version {}: {}", new Object[] { Integer.valueOf(version), url });
/* 67 */       if (version > latestVersion) {
/* 68 */         latestVersion = version;
/* 69 */         latestUrl = url;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 74 */     if (latestUrl != null) {
/* 75 */       LOGGER.info("Updating stage1 to version {} from {}", new Object[] { Integer.valueOf(latestVersion), latestUrl });
/* 76 */       try (InputStream in = latestUrl.openStream()) {
/* 77 */         Files.deleteIfExists(stage1File);
/* 78 */         Files.copy(in, stage1File, new java.nio.file.CopyOption[0]);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 83 */     return stage1File;
/*    */   }
/*    */   
/*    */   private static int getVersion(URL file) {
/* 87 */     try (JarInputStream in = new JarInputStream(file.openStream(), false)) {
/* 88 */       Manifest manifest = in.getManifest();
/* 89 */       Attributes attributes = manifest.getMainAttributes();
/* 90 */       if (!STAGE1_PKG_PATH.equals(attributes.getValue("Name"))) {
/* 91 */         return -1;
/*    */       }
/* 93 */       return Integer.parseInt(attributes.getValue("Implementation-Version"));
/* 94 */     } catch (Exception e) {
/* 95 */       LOGGER.warn("Failed to read version from " + file, e);
/* 96 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\James\OneDrive\Desktop\ChromaHUD-3.0.jar!\gg\essential\loader\stage0\EssentialLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
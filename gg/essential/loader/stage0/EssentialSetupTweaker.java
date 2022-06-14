/*    */ package gg.essential.loader.stage0;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.ITweaker;
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EssentialSetupTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   private static final String STAGE1_CLS = "gg.essential.loader.stage1.EssentialSetupTweaker";
/* 21 */   private final EssentialLoader loader = new EssentialLoader("launchwrapper");
/*    */   private final ITweaker stage1;
/*    */   
/*    */   public EssentialSetupTweaker() {
/*    */     try {
/* 26 */       this.stage1 = loadStage1(this);
/* 27 */     } catch (Exception e) {
/* 28 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ITweaker loadStage1(ITweaker stage0) throws Exception {
/* 36 */     if (Launch.minecraftHome == null) {
/* 37 */       Launch.minecraftHome = new File(".");
/*    */     }
/*    */ 
/*    */     
/* 41 */     Path stage1File = this.loader.loadStage1File(Launch.minecraftHome.toPath());
/* 42 */     URL stage1Url = stage1File.toUri().toURL();
/*    */ 
/*    */     
/* 45 */     LaunchClassLoader classLoader = Launch.classLoader;
/* 46 */     classLoader.addURL(stage1Url);
/* 47 */     classLoader.addClassLoaderExclusion("gg.essential.loader.stage1.");
/* 48 */     addUrlHack(classLoader.getClass().getClassLoader(), stage1Url);
/*    */ 
/*    */     
/* 51 */     return Class.forName("gg.essential.loader.stage1.EssentialSetupTweaker", true, (ClassLoader)classLoader)
/* 52 */       .getConstructor(new Class[] { ITweaker.class
/* 53 */         }).newInstance(new Object[] { stage0 });
/*    */   }
/*    */ 
/*    */   
/*    */   public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
/* 58 */     this.stage1.acceptOptions(args, gameDir, assetsDir, profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectIntoClassLoader(LaunchClassLoader classLoader) {
/* 63 */     this.stage1.injectIntoClassLoader(classLoader);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLaunchTarget() {
/* 68 */     return this.stage1.getLaunchTarget();
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 73 */     return this.stage1.getLaunchArguments();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void addUrlHack(ClassLoader loader, URL url) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
/* 78 */     ClassLoader classLoader = Launch.classLoader.getClass().getClassLoader();
/* 79 */     Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
/* 80 */     method.setAccessible(true);
/* 81 */     method.invoke(classLoader, new Object[] { url });
/*    */   }
/*    */ }


/* Location:              C:\Users\James\OneDrive\Desktop\ChromaHUD-3.0.jar!\gg\essential\loader\stage0\EssentialSetupTweaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
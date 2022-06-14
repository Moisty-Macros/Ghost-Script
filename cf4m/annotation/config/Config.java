package xyz.apfelmus.cf4m.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
  String name();
  
  String description() default "";
}


/* Location:              C:\Users\James\OneDrive\Desktop\ChromaHUD-3.0.jar!\xyz\apfelmus\cf4m\annotation\config\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
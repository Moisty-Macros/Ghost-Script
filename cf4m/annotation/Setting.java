package xyz.apfelmus.cf4m.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Setting {
  String name();
  
  String description() default "";
}


/* Location:              C:\Users\James\OneDrive\Desktop\ChromaHUD-3.0.jar!\xyz\apfelmus\cf4m\annotation\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
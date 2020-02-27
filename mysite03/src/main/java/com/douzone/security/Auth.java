package com.douzone.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE}) //ElementType.METHOD => 메소드에 붙겠다 ElementType.TYPE => 클래스에 붙겠다
@Retention(RetentionPolicy.RUNTIME) 
public @interface Auth {
	public enum Role{USER, ADMIN}

	public Role role() default Role.USER;
	
	String value() default "USER";
	boolean test() default false;
	
}
/*
@Retention(RetentionPolicy.RUNTIME) //컴파일 이후에도 JVM에 의해 참조가 가능
@Retention(RetentionPolicy.CLASS) //컴파일러가 클래스를 참조할 떄까지 유효함
@Retention(RetentionPolicy.SOURCE) //어노테이션 정보는 컴파일 이후 없어짐 
*/
 
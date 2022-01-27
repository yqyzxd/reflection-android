package com.github.yqyzxd.reflection

import android.mtp.MtpConstants
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * android P (9.0)后对hide方法反射限制需要系统签名或者加入白名单才可以反射hide方法
 * android Q (10)开始,禁止所有反射方法调用,需要系统签名或者加入白名单才可以反射
 */
class Reflection {

    private var mTargetClass: Class<*>? = null
    private var mMethodName: String? = null
    private var mMethodArgs = mutableListOf<Class<*>>()
    private var mFieldName: String? = null
    private var mDeclared = false
    private var mBlock: ((Exception) -> Unit)? = null

    fun on(targetClass: Class<*>): Reflection {
        mTargetClass = targetClass
        return this
    }


    fun method(methodName: String, vararg args: Class<*>): Reflection {
        mMethodName = methodName
        mMethodArgs.clear()
        mMethodArgs.addAll(args)
        return this
    }

    fun declared():Reflection{
        mDeclared=true
        return this
    }

    fun field(fieldName: String): Reflection {
        mFieldName = fieldName
        return this
    }

    fun <T> get(): T? {
        var result: T? = null
        mTargetClass?.apply {
            try {
                mMethodName?.apply {
                    val method = whichMethod()
                    result = method as T
                }
                mFieldName?.apply {
                    val field = whichField()
                    result = field as T
                }
            } catch (e: Exception) {
                mBlock?.invoke(e)?:throw e
            }
        }
        return result
    }

    fun catch(block: (e: Exception) -> Unit):Reflection {
        mBlock = block
        return this
    }

    fun <T> invoke(target: Any? = null, vararg args: Any): T? {


        mTargetClass?.apply {

            try {
                var result: Any? = null
                if (mMethodName != null) {
                    val method = whichMethod()
                    result = method?.invoke(target, args)
                } else if (mFieldName != null) {
                    val field = whichField()
                    result = field?.get(target)
                }
                return result as T?
            } catch (e: Exception) {
                mBlock?.invoke(e)?:throw e
            }


        }


        return null
    }



    private fun whichMethod():Method?{
        var method:Method?=null
        mTargetClass?.apply {
             method= if (mDeclared) {
                            Meta.getDeclaredMethod(mTargetClass,mMethodName, *mMethodArgs.toTypedArray())
                        }else {
                            Meta.getMethod(mTargetClass,mMethodName,*mMethodArgs.toTypedArray())
                        }
            method?.isAccessible = true
        }
       return method
    }

    private fun whichField():Field?{
        var field:Field?=null
        mTargetClass?.apply {
            field= if (mDeclared) {
                Meta.getDeclaredField(mTargetClass,mFieldName)
            }else {
                Meta.getField(mTargetClass,mFieldName)
            }
            field?.isAccessible = true
        }
        return field
    }

}
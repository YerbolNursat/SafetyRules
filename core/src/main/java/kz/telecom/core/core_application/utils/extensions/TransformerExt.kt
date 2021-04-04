package kz.telecom.core.core_application.utils.extensions

import kotlin.reflect.full.memberProperties

/**
 * Преобразовывает с одного Data класса в другой, если название полей одинаковые
 * Если поля не примитивные, то надо давать ему HashMap, где хранятся название полей Объектов и как их обработать,
 * также можно кастомно обрабатывать, если нужно
 */
inline fun <reified T : Any, reified V> T.transformFromOneObjectToAnother(params: HashMap<String, ((Any?) -> Any)>? = null) =
    with((V::class.constructors as List)[0]) {
        val propertiesByName = T::class.memberProperties.associateBy { it.name }
        callBy(args = parameters.associateWith { parameter ->
            val action = { propertiesByName[parameter.name]?.get(this@transformFromOneObjectToAnother) }
            if (params?.keys?.contains(parameter.name) == true) {
                params[parameter.name]?.let { it(action()) }
            } else {
                action()
            }
        })
    }
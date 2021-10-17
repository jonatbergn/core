import com.jonatbergn.core.iceandfire.app.State.Companion.list
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHousesAction
import com.jonatbergn.core.iceandfire.app.iceAndFireContext
import kotlinext.js.require
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.html.ThScope
import react.dom.a
import react.dom.attrs
import react.dom.button
import react.dom.div
import react.dom.onClick
import react.dom.render
import react.dom.span
import react.dom.table
import react.dom.tbody
import react.dom.td
import react.dom.th
import react.dom.thead
import react.dom.tr
import react.fc
import react.useEffectOnce

fun main() {
    require("./app.css")
    val model = iceAndFireContext(GlobalScope).model
    render(document.getElementById("root")) {
        child(fc("FunctionalComponent") {
            val state = model.states.collectAsViewState().list()
            useEffectOnce { model.actions.tryEmit(LoadNextHousesAction) }
            div(classes = "flex flex-col") {
                div(classes = "-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8") {
                    div(classes = "py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8") {
                        div(classes = "shadow overflow-hidden border-b border-gray-200 sm:rounded-lg") {
                            table(classes = "min-w-full divide-y divide-gray-200") {
                                thead(classes = "bg-gray-50") {
                                    tr {
                                        th(
                                            scope = ThScope.col,
                                            classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                        ) {
                                            +"Name"
                                        }
                                        th(
                                            scope = ThScope.col,
                                            classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                        ) {
                                            +"Lord"
                                        }
                                        th(
                                            scope = ThScope.col,
                                            classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                        ) {
                                            +"Status"
                                        }
                                        th(
                                            scope = ThScope.col,
                                            classes = "px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                        ) {
                                            +"Founded"
                                        }
                                        th(
                                            scope = ThScope.col,
                                            classes = "relative px-6 py-3"
                                        ) {
                                            span(classes = "sr-only") { +"Edit" }
                                        }
                                    }
                                }
                                tbody(classes = "bg-white divide-y divide-gray-200") {
                                    state.houses?.forEach {
                                        tr {
                                            td(classes = "px-6 py-4 whitespace-nowrap") {
                                                div(classes = "flex items-center") {
                                                    div(classes = "ml-4") {
                                                        div(classes = "text-sm font-medium text-gray-900") {
                                                            +it.name
                                                        }
                                                        div(classes = "text-sm text-gray-500") {
                                                            +it.region
                                                        }
                                                    }
                                                }
                                            }
                                            td(classes = "px-6 py-4 whitespace-nowrap") {
                                                div(classes = "text-sm text-gray-900") { +it.lordName }
                                                div(classes = "text-sm text-gray-500") { +it.lordGender }
                                            }
                                            td(classes = "px-6 py-4 whitespace-nowrap") {
                                                if (!it.diedOut) {
                                                    span(classes = "px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800") {
                                                        +"Active"
                                                    }
                                                }
                                            }
                                            td(classes = "px-6 py-4 whitespace-nowrap text-sm text-gray-500") {
                                                +it.founded
                                            }
                                            td(classes = "px-6 py-4 whitespace-nowrap text-right text-sm font-medium") {
                                                a(
                                                    href = "#",
                                                    classes = "text-indigo-600 hover:text-indigo-900"
                                                ) { +"Details" }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (state.isMoreHousesAvailable) {
                button(classes = "bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full") {
                    attrs { onClick = { model.actions.tryEmit(LoadNextHousesAction) } }
                    div(classes = "text-sm font-medium text-gray-900") { +"More" }
                }
            }
        }
        )
    }
}

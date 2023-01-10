import com.jonatbergn.core.iceandfire.app.AppContext
import com.jonatbergn.core.iceandfire.app.AppModule
import kotlinext.js.require
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import react.FC
import react.Fragment
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.style
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.thead
import react.dom.html.ReactHTML.tr
import web.dom.document

fun main() {
    require("./app.css")
    val appContext = AppContext(AppModule(CoroutineScope(Default)))
    val container = requireNotNull(document.getElementById("root"))
    createRoot(container).render(Fragment.create {
        FC<Props> {
            val state = appContext.state.collectAsViewState().houseListState()
            div {
                style {
                    +"flex flex-col"
                }
                div {
                    style {
                        +"-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8"
                    }
                    div {
                        style {
                            +"py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8"
                        }
                        div {
                            style {
                                +"shadow overflow-hidden border-b border-gray-200 sm:rounded-lg"
                            }
                            table {
                                style {
                                    +"min-w-full divide-y divide-gray-200"
                                }
                                thead {
                                    style {
                                        +"bg-gray-50"
                                    }
                                    tr {
                                        th {
                                            style {
                                                +"px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                            }
                                            scope = "col"
                                            +"Name"
                                        }
                                        th {
                                            style {
                                                +"px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                            }
                                            scope = "col"
                                            +"Lord"
                                        }
                                        th {
                                            style {
                                                +"px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                            }
                                            scope = "col"
                                            +"Status"
                                        }
                                        th {
                                            style {
                                                +"px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                                            }
                                            scope = "col"
                                            +"Founded"
                                        }
                                        th {
                                            style {
                                                +"relative px-6 py-3"
                                            }
                                            scope = "col"
                                            span {
                                                style {
                                                    +"sr-only"
                                                }
                                                +"Edit"
                                            }
                                        }
                                    }
                                }
                                tbody {
                                    style {
                                        +"bg-white divide-y divide-gray-200"
                                    }
                                    state.houses.forEach {
                                        tr {
                                            td {
                                                style {
                                                    +"px-6 py-4 whitespace-nowrap"
                                                }
                                                div {
                                                    style {
                                                        +"flex items-center"
                                                    }
                                                    div {
                                                        style {
                                                            +"ml-4"
                                                        }
                                                        div {
                                                            style {
                                                                +"text-sm font-medium text-gray-900"
                                                            }
                                                            +it.name
                                                        }
                                                        div {
                                                            style {
                                                                +"text-sm text-gray-500"
                                                            }
                                                            +it.region
                                                        }
                                                    }
                                                }
                                            }
                                            td {
                                                style {
                                                    +"px-6 py-4 whitespace-nowrap"
                                                }
                                                div {
                                                    style {
                                                        +"text-sm text-gray-900"
                                                    }
                                                    +it.lordName
                                                }
                                                div {
                                                    style {
                                                        +"text-sm text-gray-500"
                                                    }
                                                    +it.lordGender
                                                }
                                            }
                                            td {
                                                style {
                                                    +"px-6 py-4 whitespace-nowrap"
                                                }
                                                if (!it.diedOut) {
                                                    span {
                                                        style {
                                                            +"px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800"
                                                        }
                                                        +"Active"
                                                    }
                                                }
                                            }
                                            td {
                                                style {
                                                    +"px-6 py-4 whitespace-nowrap text-sm text-gray-500"
                                                }
                                                +it.founded
                                            }
                                            td {
                                                style {
                                                    +"px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
                                                }
                                                a {
                                                    style {
                                                        +"text-indigo-600 hover:text-indigo-900"
                                                    }
                                                    href = "#"
                                                    +"Details"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (state.isMoreHousesAvailable == true) {
//                button(classes = "bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full") {
//                    attrs {
//                        onClick = {
////                            iceAndFire.loadNextHousesPage() TODO
//                        }
//                    }
//                    div(classes = "text-sm font-medium text-gray-900") { +"More" }
//                }
            }
        }
    })
}

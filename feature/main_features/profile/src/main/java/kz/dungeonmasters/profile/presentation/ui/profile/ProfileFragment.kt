package kz.dungeonmasters.profile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.prefs.SecurityDataSource
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.model.InfoResponseUi
import kz.dungeonmasters.core.core_application.presentation.model.InfoUi
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.loadImage
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.databinding.FragmentProfileBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : CoreFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val securityDataSource: SecurityDataSource by inject()

    override val viewModel: ProfileViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        viewModel.getProfileInfo()
    }

    private fun initButtons() {
        standardInitButton(CoreButton("Выйти", (::logOut)), binding.btnLogOut.root)
        binding.btnToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.btnAchievements.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_achievementsFragment)
        }
        binding.btnToAppRules.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoResponseUi(
                        "Правила использования приложения",
                        listOf(
                            InfoUi(
                                "Неприемлемый контент",
                                "Мы стремимся к тому, чтобы в нашем приложении было безопасно и комфортно всем пользователям. Ниже перечислены виды контента, который считается неподобающим и может причинить людям вред. "
                            ),
                            InfoUi(
                                "Контент сексуального характера и непристойная лексика",
                                "Запрещено публиковать изображения, содержащие или продвигающие материалы сексуального характера, например порнографию и любой контент или услуги, предназначенные для сексуального удовлетворения. Также запрещена непристойная лексика. "
                            ),
                            InfoUi(
                                "Дискриминационные высказывания",
                                "Запрещено публиковать высказывания, пропагандирующие насилие или разжигающие ненависть к каким-либо лицам и социальным группам на почве расовой, этнической или национальной принадлежности, вероисповедания, пола, возраста, инвалидности, статуса ветерана, сексуальной ориентации, гендерной идентичности и других признаков, которые могут быть причиной систематической дискриминации или маргинализации. "
                            ),
                            InfoUi(
                                "Насилие",
                                "Запрещено публиковать изображения и высказывания , которые изображают опасные действия или неоправданное насилие, а также способствуют им."
                            ),
                            InfoUi(
                                "Издевательства и домогательства",
                                "Запрещено публиковать высказывания, содержащие угрозы, издевательства и домогательства, а также способствующие совершению таких действий."
                            ),
                        )
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
        binding.btnToConfidential.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoResponseUi(
                        "Политика конфиденциальности",
                        listOf(
                            InfoUi(
                                "СБОР И ОБРАБОТКА ПЕРСОНАЛЬНЫХ ДАННЫХ",
                                "2.1. Оператор собирает и хранит только те Персональные данные, которые необходимы для оказания Услуг Оператором и взаимодействия с Пользователем.\n" +
                                        "2.2. Персональные данные могут использоваться в следующих целях:\n" +
                                        "2.2.1 оказание Услуг Пользователю;\n" +
                                        "2.2.2 идентификация Пользователя; \n" +
                                        "2.2.3 взаимодействие с Пользователем;\n" +
                                        "2.2.4 направление Пользователю рекламных материалов, информации и запросов;\n" +
                                        "2.2.5 проведение статистических и иных исследований;\n" +
                                        "2.3. Оператор в том числе обрабатывает следующие данные:\n" +
                                        "2.3.1 фамилия, имя и отчество;\n" +
                                        "2.3.2 адрес электронной почты;\n" +
                                        "2.3.3 номер телефона (в т.ч. мобильного).\n" +
                                        "2.4. Пользователю запрещается указывать на Сайте персональные данные третьих лиц(за исключением условия представления интересов этих лиц, имея документальное подтверждение третьих лиц на осуществление таких действий).\n"
                            ),
                            InfoUi(
                                "ПОРЯДОК ОБРАБОТКИ ПЕРСОНАЛЬНЫХ И ИНЫХ ДАННЫХ",
                                "3.1. Оператор обязуется использовать Персональные данные в соответствии с Законом «О персональных данных» Республики Казахстан и внутренними документами Оператора. \n" +
                                        "3.2. В отношении Персональных данных и иных Данных Пользователя сохраняется их конфиденциальность, кроме случаев, когда указанные данные являются общедоступными. \n" +
                                        "3.3. Оператор имеет право сохранять архивную копию Персональных данных.\n" +
                                        "Оператор имеет право хранить Персональные данные и Данные на серверах вне территории Республики Казахстан.\n" +
                                        "3.4. Оператор имеет право передавать Персональные данные и Данные Пользователя без согласия Пользователя следующим лицам:\n" +
                                        "3.4.1 государственным органам, в том числе органам дознания и следствия, и органам местного самоуправления по их мотивированному запросу;\n" +
                                        "3.4.2 в иных случаях, прямо предусмотренных действующим законодательством Республики Казахстан.\n" +
                                        "3.5. Оператор имеет право передавать Персональные данные и Данные третьим лицам, не указанным в п.3.4. настоящей Политики конфиденциальности, в следующих случаях:\n" +
                                        "3.5.1 Пользователь выразил свое согласие на такие действия;\n" +
                                        "3.5.2 передача необходима в рамках использования Пользователем Сайта или оказания Услуг Пользователю;\n" +
                                        "3.6. Оператор осуществляет автоматизированную обработку Персональных данных и Данных.\n"
                            ),
                            InfoUi(
                                "ЗАЩИТА ПЕРСОНАЛЬНЫХ ДАННЫХ",
                                "4.1. Оператор осуществляет надлежащую защиту Персональных и иных данных в соответствии с Законодательством и принимает необходимые и достаточные организационные и технические меры для защиты Персональных данных.\n" +
                                        "4.2. Применяемые меры защиты в том числе позволяют защитить Персональные данные от неправомерного или случайного доступа, уничтожения, изменения, блокирования, копирования, распространения, а также от иных неправомерных действий с ними третьих лиц.\n"
                            ),
                            InfoUi(
                                "ИНЫЕ ПОЛОЖЕНИЯ",
                                "5.1. К настоящей Политике конфиденциальности и отношениям между Пользователем и Оператором, возникающим в связи с применением Политики конфиденциальности, подлежит применению право Республики Казахстан.\n" +
                                        "5.2. Все возможные споры, вытекающие из настоящего Соглашения, подлежат разрешению в соответствии с действующим законодательством по месту регистрации Оператора.\n" +
                                        "Перед обращением в суд Пользователь должен соблюсти обязательный до судебный порядок и направить Оператору соответствующую претензию в письменном виде. Срок ответа на претензию составляет 30 (тридцать) рабочих дней.\n" +
                                        "5.3. Если по тем или иным причинам одно или несколько положений Политики конфиденциальности будут признаны недействительными или не имеющими юридической силы, это не оказывает влияния на действительность или применимость остальных положений Политики конфиденциальности.\n" +
                                        "5.4. Оператор имеет право в любой момент изменять Политику конфиденциальности (полностью или в части) в одностороннем порядке без предварительного согласования с Пользователем. Все изменения вступают в силу с момента ее размещения на Сайте.\n" +
                                        "5.5. Пользователь обязуется самостоятельно следить за изменениями Политики конфиденциальности путем ознакомления с актуальной редакцией."
                            ),
                        )
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
        binding.btnToDeveloper.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoResponseUi(
                        "Связь с разработчиками",
                        listOf(
                            InfoUi(
                                "Служба поддержки",
                                "Обратитесь по почте 170107058@stu.sdu.edu.kz"
                            )
                        )
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun logOut() {
        securityDataSource.clearAuthorizedUserData()
        activity?.showActivityAndClearBackStack(requireContext(), CoreConstant.AUTH_ACTIVITY)
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: ProfileInfo) {
        data.photo?.let { binding.userIv.loadImage(it) }
        data.username?.let { binding.userNameTv.text = it }
        data.email.let { binding.emailTv.text = it }
    }

}
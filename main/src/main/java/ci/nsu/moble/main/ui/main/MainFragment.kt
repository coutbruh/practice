package ci.nsu.moble.main.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import ci.nsu.moble.main.R

class MainFragment : Fragment() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var labelPurple500: TextView
    private lateinit var labelTeal700: TextView
    private lateinit var labelPurple200: TextView
    private lateinit var labelTeal200: TextView
    private lateinit var labelPurple700: TextView

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация элементов
        editText = view.findViewById(R.id.editText)
        button = view.findViewById(R.id.button2)
        labelPurple500 = view.findViewById(R.id.labelPurple500)
        labelTeal700 = view.findViewById(R.id.labelTeal700)
        labelPurple200 = view.findViewById(R.id.labelPurple200)
        labelTeal200 = view.findViewById(R.id.labelTeal200)
        labelPurple700 = view.findViewById(R.id.labelPurple700)

        // Устанавливаем обработчик нажатия на кнопку
        button.setOnClickListener {
            changeButtonColor()
        }

        // Устанавливаем обработчики кликов на лейблы
        setupLabelClickListeners()
    }

    private fun changeButtonColor() {
        // Получаем введенный текст и удаляем лишние пробелы, приводим к нижнему регистру
        val inputText = editText.text.toString().trim().lowercase()

        if (inputText.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название цвета", Toast.LENGTH_SHORT).show()
            return
        }

        // Определяем цвет по введенному тексту
        val colorRes = when (inputText) {
            "purple", "purple_500", "purple500", "фиолетовый" -> R.color.purple_500
            "teal", "teal_700", "teal700", "бирюзовый" -> R.color.teal_700
            "purple_200", "purple200", "светло-фиолетовый" -> R.color.purple_200
            "teal_200", "teal200", "светло-бирюзовый" -> R.color.teal_200
            "purple_700", "purple700", "темно-фиолетовый" -> R.color.purple_700
            else -> {
                // Если цвет не найден
                Toast.makeText(
                    requireContext(),
                    "Цвет не найден. Доступные цвета: purple, teal, purple_200, teal_200, purple_700",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
        }

        // Меняем цвет фона кнопки
        button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorRes)

        // Показываем сообщение об успешной смене цвета
        Toast.makeText(
            requireContext(),
            "Цвет кнопки изменен на $inputText",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupLabelClickListeners() {
        // При клике на лейбл Purple 500
        labelPurple500.setOnClickListener {
            editText.setText("purple_500")
            changeButtonColor()
        }

        // При клике на лейбл Teal 700
        labelTeal700.setOnClickListener {
            editText.setText("teal_700")
            changeButtonColor()
        }

        // При клике на лейбл Purple 200
        labelPurple200.setOnClickListener {
            editText.setText("purple_200")
            changeButtonColor()
        }

        // При клике на лейбл Teal 200
        labelTeal200.setOnClickListener {
            editText.setText("teal_200")
            changeButtonColor()
        }

        // При клике на лейбл Purple 700
        labelPurple700.setOnClickListener {
            editText.setText("purple_700")
            changeButtonColor()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
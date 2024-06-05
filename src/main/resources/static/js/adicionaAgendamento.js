// script.js
document.addEventListener('DOMContentLoaded', function() {
    const datepicker = document.getElementById('floatingInput2');
    const calendar = document.getElementById('calendar');
    const diasDisponibilidadeInput = document.getElementById('diasDisponibilidade');
    const availableDays = diasDisponibilidadeInput.value.split(',').map(Number);

    datepicker.addEventListener('focus', showCalendar);
    document.addEventListener('click', function(e) {
        if (!calendar.contains(e.target) && e.target !== datepicker) {
            calendar.style.display = 'none';
        }
    });

    function showCalendar() {
        calendar.innerHTML = '';
        const currentDate = new Date();
        const month = currentDate.getMonth();
        const year = currentDate.getFullYear();
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        const daysOfWeek = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];

        // Adicionar cabeçalhos dos dias da semana
        daysOfWeek.forEach(day => {
            const dayHeader = document.createElement('div');
            dayHeader.textContent = day;
            dayHeader.classList.add('font-weight-bold');
            calendar.appendChild(dayHeader);
        });

        // Adicionar divs vazias somente para os dias que antecedem o primeiro dia do mês
        for (let i = 0; i < firstDay; i++) {
            const emptyCell = document.createElement('div');
            emptyCell.style.visibility = 'hidden';  // Ocultar as divs vazias
            calendar.appendChild(emptyCell);
        }

        for (let date = 1; date <= lastDate; date++) {
            const dateCell = document.createElement('div');
            const dateString = `${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}`;
            const dayOfWeek = new Date(year, month, date).getDay();

            dateCell.textContent = date;
            if (!availableDays.includes(dayOfWeek)) {
                dateCell.classList.add('disabled');
            } else {
                dateCell.addEventListener('click', function() {
                    datepicker.value = dateString;
                    calendar.style.display = 'none';
                    highlightSelectedDate(date);
                });
            }
            calendar.appendChild(dateCell);
        }

        calendar.style.display = 'grid';
    }

    function highlightSelectedDate(selectedDate) {
        const dateCells = calendar.querySelectorAll('div');
        dateCells.forEach(cell => {
            cell.classList.remove('selected');
            if (cell.textContent == selectedDate) {
                cell.classList.add('selected');
            }
        });
    }
});

<template>
    <table id="checkboxTableComponent" class="table table-bordered table-striped">
        <thead>
            <tr>
                <th  v-for="field in fields" :key='field' @click="sortTable(field)" > 
                {{field}} <i class="bi bi-sort-alpha-down" aria-label='Sort Icon'></i>
                </th>
                <th>
                    Select
                </th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in selectableTableData" :key='item'>
                <td v-for="field in fields" :key='field'>{{item[field]}}</td>
                <td>
                    <div class="form-check d-flex justify-content-center">
                        <input v-model="item.selected" class="form-check-input" type="checkbox">
                    </div>
                </td>
            </tr>
        </tbody>
    </table> 
</template>
<script>
    import { orderBy} from 'lodash';
    export default {
        name: 'CheckboxTableComponent',
        props:{
            // 
            tableData:{
                type: Array,
            },
            fields:{    
                type: Array,
            },
        },
        data() {
            return {
                sortAsc: true,
                sortBy: this.fields[0],
                selectableTableData: [],
                updatedList: this.tableData,
                //selected: [],
            }
        },
        methods: {
        // a function to sort the table
            sortTable(col){
                if (this.sortBy == col) {
                    this.sortAsc = !this.sortAsc
                } else {
                    this.sortBy = col
                    this.sortAsc = true
                }

                this.selectableTableData = orderBy(this.selectableTableData,col, this.sortAsc ? "asc" : "desc")
            },
            selected() {

                let filteredRows = this.selectableTableData.filter((selectableTableData) => selectableTableData.selected)
                let pureData = []
                for (const row of filteredRows) {
                    /* eslint-disable no-unused-vars */
                    let pureRow = (({ selected, ...object }) => object)(row);
                    /* eslint-enable no-unused-vars */
                    pureData.push(pureRow);
                }
                return pureData
            },
        },
        mounted() {
            this.selectableTableData = this.tableData;
            this.selectableTableData.forEach((element) => {
                element.selected = false;
            });
        }
    }
</script>
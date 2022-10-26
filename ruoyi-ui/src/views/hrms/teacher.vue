<template>
  <div class="app-container">
    <!-- <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input
          v-model="queryParams.gender"
          placeholder="请输入性别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input
          v-model="queryParams.idCard"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="department">

        <el-select v-model="queryParams.department" placeholder="请选择">
          <el-option
            v-for="item in dict.type.department_name"
            :key="item.value"
            :label="item.label"
            placeholder="请输入部门"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form> -->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['teacher:info:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['teacher:info:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['teacher:delete']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['teacher:info:export']"
          >导出</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="genderQrCode"
          v-hasPermi="['teacher:info:export']"
          >生成填写表单二维码</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="infoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="teacherId" />
      <el-table-column
        :label="child.name"
        align="center"
        :prop="child.name"
        v-for="(child, index) of dynamicFields"
        
        :key="child.id"
      >
        <template slot-scope="scope">
          <span
            v-if="
              getDynamicChild(scope.row.teacherId, child.name).validate &&
              getDynamicChild(scope.row.teacherId, child.name).validate
                .dateTimeFormat
            "
            >{{
              formatDate(
                getDynamicChild(scope.row.teacherId, child.name).value,
                getDynamicChild(scope.row.teacherId, child.name).validate
                  .dateTimeFormat
              )
            }}</span
          >
          <span
            v-else-if="getDynamicChild(scope.row.teacherId, child.name).dicList"
            >{{ getDynamicDic(scope.row.teacherId, child.name) }}</span
          >
          <span v-else>{{
            getDynamicChild(scope.row.teacherId, child.name).value
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['teacher:info:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['teacher:delete']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.mPageNum"
      :limit.sync="queryParams.mPageSize"
      @pagination="getList"
    />

    <el-dialog title="生成二维码" :visible.sync="qrCodeVisiable" width="400px">
      <div class="qrCode">
        <vue-qr
          :size="191"
          :margin="0"
          :auto-color="true"
          :dot-scale="1"
          :text="baseQrCodeUrl + qrCode"
        />
      </div>
    </el-dialog>

    <!-- 添加或修改teacher对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="出生年月" prop="birth">
          <el-date-picker
            clearable
            v-model="form.birth"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生年月"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-select v-model="form.department" placeholder="请选择">
            <el-option
              v-for="item in dict.type.department_name"
              :key="item.value"
              :label="item.label"
              placeholder="请输入部门"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listInfo,
  getInfo,
  delInfo,
  addInfo,
  updateInfo,
  getQrCodeId,
} from "@/api/teacher/info";
import VueQr from "vue-qr";
import dayjs from "dayjs";

export default {
  name: "Info",
  dicts: ["department_name"],
  components: {
    VueQr,
  },
  computed: {
    dynamicFields() {
      return this.infoList[0]?.children || [];
    },
  },
  data() {
    return {
      qrCode: null,
      baseQrCodeUrl: "http://localhost:1024/teacher?code=",
      qrCodeVisiable: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // teacher表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        mPageNum: 1,
        mPageSize: 10,
        name: null,
        gender: null,
        idCard: null,
        birth: null,
        age: null,
        department: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        gender: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        idCard: [
          { required: true, message: "身份证号不能为空", trigger: "blur" },
        ],
        birth: [
          { required: true, message: "出生年月不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    formatDate(date, format) {
      if (date) {
        return dayjs(parseInt(date)).format(format);
      } else {
        return "";
      }
    },
    getDynamicChild(id, name) {
      const children = this.infoList.find(
        (item) => item.teacherId == id
      ).children;
      return children.find((child) => child.name == name);
    },
    getDynamicDic(teacherId, name) {
      return this.getDynamicChild(teacherId, name).dicList.find(
        (dic) => dic.value == this.getDynamicChild(teacherId, name).value
      )?.label;
    },
    async genderQrCode() {
      this.loading = true;
      try {
        const result = await getQrCodeId();
        this.qrCode = result.message;
        this.qrCodeVisiable = true;
      } finally {
        this.loading = false;
      }
    },
    /** 查询teacher列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then((response) => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        teacherId: null,
        name: null,
        gender: null,
        idCard: null,
        birth: null,
        age: null,
        department: null,
        remark: null,
        createTime: null,
        updateTime: null,
        updateBy: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.mPageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.teacherId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加teacher";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const teacherId = row.teacherId || this.ids;
      getInfo(teacherId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改teacher";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.teacherId != null) {
            updateInfo(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const teacherIds = row.teacherId || this.ids;
      this.$modal
        .confirm('是否确认删除teacher编号为"' + teacherIds + '"的数据项？')
        .then(function () {
          return delInfo(teacherIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "teacher/info/export",
        {
          ...this.queryParams,
        },
        `info_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style lang="scss" scoped>
.qrCode {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>

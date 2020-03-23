<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<tr>
	<td colspan="3">
		<div class="table-title closed-top-title">
			审核信息
		</div>
	</td>
</tr>

<tr>
	<td class="first">
		<div class="inputLabel">
			<label>
				拒绝原因:
			</label>
		</div>
	</td>
	<td class="second" colspan="2">
		<div class="inputField">
			<span> <textarea id="refusedMsg-${principal.id}" rows="50" cols="50">${principal.flowMsg }</textarea> </span>
		</div>
	</td>
</tr>
<tr>
	<td class="first">
		<div class="inputLabel">
			<label>
				<button type="button"  class="button_u"
				onmousemove="this.className='button_f'"
				onmousedown="this.className='button_d'"
				onmouseout="this.className='button_u'" onclick="auditPass('${principal.id}')">审核通过</button>
			&nbsp;
			</label>
		</div>
	</td>
	<td class="second" colspan="2">
		<div class="inputLabel">
			<label>
				<button type="button"  class="button_u"
				onmousemove="this.className='button_f'"
				onmousedown="this.className='button_d'"
				onmouseout="this.className='button_u'" onclick="auditRefused('${principal.id}','refusedMsg-${principal.id}')">审核拒绝</button>
			&nbsp;
			</label>
		</div>
	</td>
</tr>

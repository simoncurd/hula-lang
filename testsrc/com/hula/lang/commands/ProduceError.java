/**
 * Copyright 2013 Simon Curd <simoncurd@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hula.lang.commands;

import com.hula.lang.runtime.RuntimeConnector;
import com.hula.lang.runtime.exception.HulaRuntimeException;

public class ProduceError extends AbstractCommand
{

	@Override
	public void execute(RuntimeConnector connector)
	{
		String mode = this.getSignatureParameter("mode");
		String code = this.getSignatureParameter("code");

		if (code == null)
		{
			code = "fake.error";
		}

		if (mode == null)
		{
			throw new HulaRuntimeException(code, "a fake error", null);
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import SvgIcon, { SvgIconProps } from '@material-ui/core/SvgIcon';

export const IfIcon = (props: SvgIconProps) => {
  return (
    <SvgIcon
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 -960 960 960"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <path d="M160-448v-60h306l232-232H575v-60h225v225h-60v-122L491-448H160Zm415 288v-60h123L524-395l42-42 174 174v-122h60v225H575Z" />
    </SvgIcon>
  );
};

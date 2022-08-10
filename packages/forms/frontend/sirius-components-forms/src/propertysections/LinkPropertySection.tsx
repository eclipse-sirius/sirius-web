/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import Link from '@material-ui/core/Link';
import { LinkPropertySectionProps } from './LinkPropertySection.types';

/**
 * Defines the content of a Link property section.
 */
export const LinkPropertySection = ({ widget }: LinkPropertySectionProps) => {
  return (
    <div>
      <Link id={widget.id} href={widget.url} rel="noopener noreferrer" target="_blank">
        {widget.label}
      </Link>
    </div>
  );
};

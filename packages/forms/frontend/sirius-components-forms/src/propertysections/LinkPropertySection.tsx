/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import Link from '@mui/material/Link';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLLink } from '../form/FormEventFragments.types';
import { HelpTooltip } from './HelpTooltip';
import { LinkStyleProps } from './LinkPropertySection.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<LinkStyleProps>()((theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
  style: {
    color: color ? getCSSColor(color, theme) : null,
    fontSize: fontSize ? fontSize : null,
    fontStyle: italic ? 'italic' : null,
    fontWeight: bold ? 'bold' : null,
    textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

/**
 * Defines the content of a Link property section.
 */
export const LinkPropertySection: PropertySectionComponent<GQLLink> = ({
  editingContextId,
  formId,
  widget,
}: PropertySectionComponentProps<GQLLink>) => {
  const props: LinkStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyle(props);

  return (
    <div className={classes.propertySectionLabel}>
      <Link className={classes.style} id={widget.id} href={widget.url} rel="noopener noreferrer" target="_blank">
        {widget.label}
      </Link>
      {widget.hasHelpText ? (
        <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id} />
      ) : null}
    </div>
  );
};

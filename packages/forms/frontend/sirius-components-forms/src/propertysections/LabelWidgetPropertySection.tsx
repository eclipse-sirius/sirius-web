/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLLabelWidget } from '../form/FormEventFragments.types';
import { LabelStyleProps } from './LabelWidgetPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';

const useStyle = makeStyles<LabelStyleProps>()(
  (theme, { color, fontSize, italic, bold, underline, strikeThrough }) => ({
    style: {
      color: color ? getCSSColor(color, theme) : undefined,
      fontSize: fontSize ? fontSize : undefined,
      fontStyle: italic ? 'italic' : undefined,
      fontWeight: bold ? 'bold' : undefined,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      verticalAlign: 'baseline',
      alignItems: 'center',
      display: 'flex',
    },
    propertySection: {
      display: 'flex',
      flexDirection: 'row',
      gap: theme.spacing(2),
    },
  })
);

export const LabelWidgetPropertySection: PropertySectionComponent<GQLLabelWidget> = ({
  editingContextId,
  formId,
  widget,
}: PropertySectionComponentProps<GQLLabelWidget>) => {
  const props: LabelStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const { classes } = useStyle(props);
  return (
    <div className={classes.propertySection}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <Typography className={classes.style}>{widget.stringValue}</Typography>
    </div>
  );
};

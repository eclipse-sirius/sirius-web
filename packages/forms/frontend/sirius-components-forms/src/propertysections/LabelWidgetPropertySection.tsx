/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { LabelStyleProps, LabelWidgetPropertySectionProps } from './LabelWidgetPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles<Theme, LabelStyleProps>(() => ({
  style: {
    color: ({ color }) => (color ? color : 'inherit'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
  },
}));

export const LabelWidgetPropertySection = ({ widget, subscribers }: LabelWidgetPropertySectionProps) => {
  const props: LabelStyleProps = {
    color: widget.style?.color ?? null,
    fontSize: widget.style?.fontSize ?? null,
    italic: widget.style?.italic ?? null,
    bold: widget.style?.bold ?? null,
    underline: widget.style?.underline ?? null,
    strikeThrough: widget.style?.strikeThrough ?? null,
  };
  const classes = useStyle(props);
  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <Typography className={classes.style}>{widget.stringValue}</Typography>
    </div>
  );
};

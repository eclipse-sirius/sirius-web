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
import { makeStyles, Theme } from '@material-ui/core/styles';
import { widgetToPropertySection } from 'properties/PropertySectionOperations';
import {
  FlexboxContainerPropertySectionProps,
  FlexboxContainerPropertySectionStyleProps,
} from 'properties/propertysections/FlexboxContainerPropertySection.types';
import { PropertySectionLabel } from 'properties/propertysections/PropertySectionLabel';
import React from 'react';

const useWidgetContainerPropertySectionStyles = makeStyles<Theme, FlexboxContainerPropertySectionStyleProps>(
  (theme) => ({
    container: {
      display: 'flex',
      flexWrap: ({ flexWrap }) => flexWrap,
      flexDirection: ({ flexDirection }) => flexDirection,
      '& > *': {
        marginBottom: theme.spacing(0),
      },
    },
    children: {
      flexGrow: ({ flexGrow }) => flexGrow,
    },
  })
);

export const FlexboxContainerPropertySection = ({
  editingContextId,
  formId,
  widget,
  widgetSubscriptions,
  setSelection,
  readOnly,
}: FlexboxContainerPropertySectionProps) => {
  const classes = useWidgetContainerPropertySectionStyles({
    flexDirection: widget.flexDirection,
    flexWrap: widget.flexWrap,
    flexGrow: widget.flexGrow,
  });

  let children = widget.children.map((widget) => (
    <div className={classes.children}>
      {widgetToPropertySection(editingContextId, formId, widget, widgetSubscriptions, setSelection, readOnly)}
    </div>
  ));

  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={[]} />
      <div className={classes.container}>{children}</div>
    </div>
  );
};

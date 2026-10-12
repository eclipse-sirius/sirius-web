/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import Checkbox from '@mui/material/Checkbox';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {
  ProjectStyleCustomizationsCheckboxParams,
  ProjectStyleCustomizationsCheckboxProps,
} from './ProjectStyleCustomizationsCheckbox.types';
import { useUpdateProjectStyleCustomizationState } from './useUpdateProjectStyleCustomizationState';

export const ProjectStyleCustomizationsCheckbox = ({
  styleCustomizationDescriptionId,
  disabled,
  checked,
  onUpdated,
}: ProjectStyleCustomizationsCheckboxProps) => {
  const { projectId } = useParams<ProjectStyleCustomizationsCheckboxParams>();

  const { updateProjectStyleCustomizationState, updated, loading } = useUpdateProjectStyleCustomizationState();

  useEffect(() => {
    if (!loading && updated) {
      onUpdated();
    }
  }, [updated, onUpdated, loading]);

  return (
    <Checkbox
      disabled={disabled || !projectId || loading}
      checked={checked}
      onChange={(event) =>
        updateProjectStyleCustomizationState(projectId ?? '', styleCustomizationDescriptionId, event.target.checked)
      }
    />
  );
};
